/*
 * Copyright (C) 2022 Thibault B.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.thibaultbee.streampack.internal.muxers

import io.github.thibaultbee.streampack.data.Config
import io.github.thibaultbee.streampack.internal.data.Frame
import io.github.thibaultbee.streampack.internal.interfaces.IOrientationProvider
import io.github.thibaultbee.streampack.internal.interfaces.Streamable

interface IMuxer: Streamable<Unit> {
    val helper: IMuxerHelper

    var orientationProvider: IOrientationProvider
    var listener: IMuxerListener?

    fun encode(frame: Frame, streamPid: Int)
    fun addStreams(streamsConfig: List<Config>): Map<Config, Int>
}