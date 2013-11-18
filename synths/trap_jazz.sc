Pdef(\break,
		Ppar([
		Pbind(\instrument, \kick, \dur, 1/4, \decay, Pstep([2,0.3],1/4,inf) * 0.7, \octave, 4, \amp, Pstep([1,0,0,1, 0,0,1,0, 0,0.2,1,0, 0,0,0,0],1/4,inf)),
		Pbind(\instrument, \ch,
			\decay, 0.3,
		\dur, 1/Pstep([8,4,4,4,1,1,6,1],1,inf),
		\decay, Pn(Pseg([0.3,0.1],1)) / 2,
		\amp, Pstep([0,0,0,0, 1,0,0,0, 0,0,0,0, 1,0,0,0],1/4,inf) + (0.1*Pn(Pseg([0,0.4],1)))
	)
		])
).play;

TempoClock.default.tempo = 100/60

~echo = Synth(\dubecho, [\length, TempoClock.default.beatDur*(1/8), \fb, 0.7, \sep, 0.0012], addAction: \addToTail);
~echo.free;

Pdef(\x).play;
Pdef(\x).stop;
Pdef(\break).quant_(4);

SynthDef(\kick, { | decay = 0.03, amp = 1, freq = 40 |
var f = SinOsc.ar(freq*XLine.ar(1,1/4,0.1));
f = f * EnvGen.kr(Env.perc(0.0001,decay), doneAction:2);
Out.ar(0,f.dup * amp * 0.8);
}).store;

SynthDef(\pip, { | freq = 440, sustain = 1, amp = 1 |
var mfreq = freq;
mfreq = mfreq.cpsmidi + (LFNoise2.ar(12).range(-1,1) * (1/8));
mfreq = mfreq.midicps;
Out.ar(0, XLine.ar(1, 1/1000, sustain * 2, doneAction: 2) * Splay.ar(VarSaw.ar(mfreq * [0.99,0.499,2.001,0.5001,1] * 2, width: XLine.ar(0.5,0.05,sustain)) ,1) * 0.3 * amp);
}).store;

SynthDef(\ch, { | decay = 3, amp = 1, freq = 440 |
f = WhiteNoise.ar;
f = LPF.ar(f, 12000);
//f = f+ DelayC.ar(f, 0.1,LFNoise2.ar(1/2).range(0.001,0.01));
f = HPF.ar(f, 4000, 0.05);
f = f * EnvGen.kr(Env.perc(0.01,decay*0.8), doneAction:2);
Out.ar(0, 15 * f.dup * amp);
}).store;
