/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.list;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link StickyMap}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class StickyMapTest {

    @Test
    public void ignoresChangesInMap() throws Exception {
        final AtomicInteger size = new AtomicInteger(2);
        final Map<Integer, Integer> map = new StickyMap<>(
            new IterableAsMap<>(
                () -> new RepeatIterator<>(
                    () -> new MapEntry<>(
                        new SecureRandom().nextInt(),
                        1
                    ),
                    size.incrementAndGet()
                )
            )
        );
        MatcherAssert.assertThat(
            "Can't ignore the changes in the underlying map",
            map.size(),
            Matchers.equalTo(map.size())
        );
    }

    @Test
    public void decoratesEntries() throws Exception {
        MatcherAssert.assertThat(
            "Can't decorate a list of entries",
            new StickyMap<String, String>(
                new MapEntry<>("first", "Jeffrey"),
                new MapEntry<>("last", "Lebowski")
            ),
            Matchers.hasValue(Matchers.endsWith("ski"))
        );
    }

}