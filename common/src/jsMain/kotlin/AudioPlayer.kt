import org.w3c.dom.Audio as JsAudio

/*
 * Copyright 2020 Mark Injerd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

actual class AudioPlayer actual constructor(
	private val filename: String,
	private val doLoop: Boolean,
	private val loopCallback: () -> Unit
) {
	private val isMusic = filename.endsWith(".mid")
	private val jsAudio = JsAudio(filename).apply { loop = doLoop }

	actual fun start() {
		if (isMusic) {
			@Suppress("UNUSED_VARIABLE")
			val filename = filename
			js("MIDIjs.play(filename)")
		} else {
			jsAudio.play()
		}
	}

	actual fun stop() {
		if (isMusic) {
			js("MIDIjs.stop()")
		} else {
			jsAudio.pause()
			jsAudio.currentTime = 0.0
		}
	}
}
