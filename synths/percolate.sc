SynthDef(\ch, { | decay = 0.03, amp = 1, freq = 440 |
var f = SinOsc.ar(freq * 4, width: Rand(1,8) / 8);
f = f + AllpassC.ar(f,0.1,0.001*(1+(LFNoise2.ar(1/8).range(-1,1)*0.06)), 0);
f = HPF.ar(f, 8000, 0.4) * 0.4;
f = f * EnvGen.kr(Env.perc(0.0001,decay), doneAction:2);
Out.ar(0,amp * 1.3 * f.dup);
}).store;

SynthDef(\kick, { | decay = 0.03, amp = 1 |
var f = SinOsc.ar(30);
f = f * EnvGen.kr(Env.perc(0.0001,decay), doneAction:2);
Out.ar(0,amp * f.dup / 4);
}).store;

Ptpar([
0,Pbind(
\instrument, \kick,
\dur, Pseq([1,[3,3,2]].convertRhythm,inf),
\decay, 0.1 * 8,
\amp, Pn(Pseg([1,0.7],1))
),
4,Pbind(
\instrument, \ch,
\dur, Pseq([Pstep([1/16],3),Pseg([1/16,0.03],1)],inf),
\freq, Pn(Pseg([100,800],2)),
//\decay, Pseq([Pseg([0.1,0.02],2),Pstep([0.03],2)],inf) * 0.8,
\amp, Pn(Pseg([1,0.7],1))
)
]).play;
