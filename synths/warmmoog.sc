(
{
	var freq = 40.midicps * 2 * MouseButton.kr(0,9, 1).midiratio;
	Splay.ar(
		inArray: MoogFF.ar(
			in:
			(Pulse.ar(
				freq: freq * LFNoise2.ar([1/4,1/4]).range(1,1.01),
				width: LFNoise2.ar(1/4).range(0.7,0.1)
			) + SinOsc.ar(freq)) * SinOsc.ar(1/8).range(1,8),
			freq: XLine.ar(8,4, 4) * freq,
			gain: LFNoise2.ar(3).range(1,3)
		).tanh * 0.1,
		spread: 1
	).dup
}.play
)
