package io.github.thibaultbee.streampack.internal.utils.av.audio.aac

import android.media.AudioFormat
import android.media.MediaCodecInfo
import android.media.MediaFormat
import io.github.thibaultbee.streampack.data.AudioConfig
import io.github.thibaultbee.streampack.internal.utils.av.audio.AudioSpecificConfig
import io.github.thibaultbee.streampack.internal.utils.extensions.extractArray
import io.github.thibaultbee.streampack.utils.ResourcesUtils
import org.junit.Assert.assertArrayEquals
import org.junit.Test
import java.nio.ByteBuffer

class AudioMuxElementTest {
    @Test
    fun `test StreamMuxConfig`() {
        // Only first 44 bits
        val expectedAudioMuxElement = byteArrayOf(0x40, 0x00, 0x24, 0x10, 0x3F, 0xC0.toByte())

        val config = AudioConfig(
            mimeType = MediaFormat.MIMETYPE_AUDIO_AAC,
            sampleRate = 44100,
            channelConfig = AudioFormat.CHANNEL_IN_MONO,
            profile = MediaCodecInfo.CodecProfileLevel.AACObjectLC
        )

        val esds = AudioSpecificConfig.fromAudioConfig(config).toByteBuffer()
        val streamMuxConfig = StreamMuxConfig.fromEsds(esds)
        assertArrayEquals(
            expectedAudioMuxElement,
            streamMuxConfig.toByteBuffer().extractArray()
        )
    }

    @Test
    fun `test AudioMuxElement`() {
        val expectedAudioMuxElement = ByteBuffer.wrap(
            ResourcesUtils.readResources("test-samples/audio/latm/aac-lc-44100Hz-mono/audio-mux-element")
        )

        val payload = ByteBuffer.wrap(
            ResourcesUtils.readResources("test-samples/audio/latm/aac-lc-44100Hz-mono/frame.raw")
        )

        val config = AudioConfig(
            mimeType = MediaFormat.MIMETYPE_AUDIO_AAC,
            sampleRate = 44100,
            channelConfig = AudioFormat.CHANNEL_IN_MONO,
        )

        val esds = AudioSpecificConfig.fromAudioConfig(config).toByteBuffer()
        val audioMuxElement = AudioMuxElement.fromEsds(payload, esds)
        assertArrayEquals(
            expectedAudioMuxElement.array(),
            audioMuxElement.toByteBuffer().extractArray()
        )
    }
}