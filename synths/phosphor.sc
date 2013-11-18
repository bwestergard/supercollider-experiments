var a,b;
~r = [2,[3,3,3,3,4]].convertRhythm;
//~r = [2,[[3,[2,1]],[3,[1,[2,1!2]]],3,3,4]].convertRhythm;
//~r = [2,[[3,[2,1]],3,[3,[2,1]],[3,[1,1,[1,1!2]]],[4,[3,1]]]].convertRhythm
// [2,[3,[3,[1,2]],3,3,[4,[1,[5,[1,1,1]]]]]].convertRhythm

~echo = Synth(\dubecho, [\length, TempoClock.default.tempo*(3/8), \fb, 0.8, \gate, 1, \sep, 0.0012], addAction: \addToTail);
//~echo.free;
//~echo.set(\sep, 0.0002)
//~echo.set(\fb, 0.9)
//~echo.set(\length, TempoClock.default.tempo*(3/8))

a = Pbind(
\instrument, \bloop,
\note, 40 + Pseq([-12,7,14,16,19,16,14,16] ++ [\rest].stutter(8) ++ [-14,7,14,16,19] ++ [\rest].stutter(11), inf) + [12],
\dur, 1/8,
\legato, Pseq([5,4,3,2,1,1,3,1],inf)
);

b = Pbind(
\instrument, \cfcf,
\freq, Pstep([40,40,40,42+12,38,38,\rest,\rest],1/2,inf).midicps * [1,2.001,1/2],
\strum, Pstep([0,0,0,1/8],1/2,inf),
\dur, Pn(Plazy{ Pseq(~r) }),
\legato, 1
);

Ptpar([0,a,16,b]).play;

(
SynthDef(\bloop, { | note = 40, gate = 1, sustain = 1| 
var z;
note = [note + (LFNoise2.ar(1/2).range(-1,1) * 0.0012),note + (LFNoise2.ar(1/2).range(-1,1) * 0.0012)];
z = VarSaw.ar(note.midicps, width: LFNoise1.ar(3).range(0,1)) * 0.3; // Varsaw sounds good too... better?
z = EnvGen.kr(Env.adsr, gate, doneAction:2) * z * LFTri.ar(Rand(3,6)/TempoClock.default.tempo).range(XLine.ar(1,0.6,sustain/2),1);
//z = RLPF.ar(z, XLine.ar(20000,note.midicps*2) * XLine.ar(0.1,1,0.001), 0.8) * 2;
Out.ar(0, z * 0.3)
}).store;
)

SynthDef(\cfcf,{|freq = 40,amp = 2, sustain = 1|
var saw,blok,tone,gate,freq_env,res_env,note;
sustain = Clip.kr(sustain, (35/60)*2, 100);
note = freq.cpsmidi + LFNoise2.ar(12).range(1/12,-1/12);
gate = Impulse.ar(0);
freq_env = EnvGen.ar(Env.new([note.midicps, 2000, 20], [3*sustain/8, 3*sustain/8]/2,'exponential'),gate);
res_env = XLine.ar(0.1,0.9,sustain/2);
saw = VarSaw.ar(note.midicps, width: 0.4, mul: 0.5) + VarSaw.ar(note.midicps*4, width: 0.4, mul: 0.4);
blok = Pulse.ar(note.midicps, width: 0.4);
tone = XFade2.ar(saw,blok,XLine.ar(1,1/2,sustain)) * 0.2;
tone = MoogLadder.ar(tone, XLine.ar(8000,100,sustain/3), res_env) * 4;
tone = tone + SinOsc.ar(note.midicps, mul: 0.5);
tone = Compander.ar(tone, tone, thresh: 1/4, slopeAbove:1/40, clampTime: 1/80) * 4;
tone = tone * XLine.ar(1,1/1000,sustain, doneAction:2);
Out.ar(0,tone.dup * amp);
}).store;

