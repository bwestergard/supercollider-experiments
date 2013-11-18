SynthDef(\blopp, { | freq = 440, sustain = 1, o = 1 |
var s = Splay.ar(Blip.ar(freq * [1.99,1,0.498,1.01,2.001], o),0.4).dup / 5;
s = s * XLine.ar(1,1/1000,sustain,doneAction:2);
s = RLPF.ar(s,XLine.ar(1/10000,1,0.001) * XLine.ar(2000,200,sustain/2), 0.8);
s = s * AmpComp.kr(freq) * 0.4;
Out.ar(0, s);
}).store;

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

// Pdefs

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
);

Pdef(\newage,
	Pchain(~swingify,
		Pbind(
			\instrument, \blopp,
			\scale, Pstep([
					[0,3,7,10],
					[0,3,5,7,10]+5,
					],2,inf),
			\dur, 2/Pstep([2,6,8,16],1,inf),
			\degree, Pn(Pseries(0,Pwhite(-1,4),8)),
			\legato, Pstep([2,1],1,inf) * 3,
			\octave, Pstep([3,4,3,5, 5,5,5,5],1/4,inf),
			\o, Pstep(
					Pseq([1,2,5,10],1/4),
					1/4,
					inf
				)
		),
	(swingBase: 1/8, swingAmount: 0.4))
);

Pdef(\metro,
Pbind(
	\instrument, \pip,
	\dur, 1,
	
)
).play;

Pdef(\newage).play

