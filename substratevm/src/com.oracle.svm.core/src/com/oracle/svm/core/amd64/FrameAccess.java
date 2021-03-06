/*
 * Copyright (c) 2014, 2017, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.oracle.svm.core.amd64;

import org.graalvm.compiler.api.replacements.Fold;
import org.graalvm.compiler.core.common.type.Stamp;
import org.graalvm.compiler.core.common.type.StampFactory;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;
import org.graalvm.nativeimage.c.function.CodePointer;
import org.graalvm.word.Pointer;

import com.oracle.svm.core.config.ConfigurationValues;

import jdk.vm.ci.meta.JavaKind;

public final class FrameAccess {

    @Platforms(Platform.AMD64.class)
    public static CodePointer readReturnAddress(Pointer sourceSp) {
        /* Read the return address, which is stored just below the stack pointer. */
        return (CodePointer) sourceSp.readWord(-returnAddressSize());
    }

    @Platforms(Platform.AMD64.class)
    public static void writeReturnAddress(Pointer sourceSp, CodePointer newReturnAddress) {
        sourceSp.writeWord(-returnAddressSize(), newReturnAddress);
    }

    /* TODO move these methods to a different class. */

    @Fold
    public static int returnAddressSize() {
        return ConfigurationValues.getTarget().arch.getReturnAddressSize();
    }

    public static int wordSize() {
        return ConfigurationValues.getTarget().arch.getWordSize();
    }

    public static int uncompressedReferenceSize() {
        return wordSize();
    }

    public static JavaKind getWordKind() {
        return ConfigurationValues.getTarget().wordJavaKind;
    }

    public static Stamp getWordStamp() {
        return StampFactory.forKind(ConfigurationValues.getTarget().wordJavaKind);
    }
}
