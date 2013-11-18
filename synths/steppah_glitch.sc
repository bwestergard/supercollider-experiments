SynthDef(\dynamik, {|freq = 440, sustain = 1, gate = 1, start = 1, end = 1, wobble = 1|
var tone,env,wenv;
env = Env.adsr(8/freq, 1/8, 0.9, 0.01, 1, -4);
freq = freq * [1,1.01];
tone = VarSaw.ar(freq, width: LFNoise2.ar(3).range(0.4,0.5));
//tone = tone * LFPulse.ar(freq, width: 0.1).range(1,XLine.ar(1,2,sustain));
tone = (tone * 1.3).tanh;
tone = tone * EnvGen.kr(env, gate, doneAction: 2) * XLine.ar(start,end,sustain) * (2/3);
tone = tone * SinOsc.ar(wobble).range(0,1);
Out.ar(0,tone);
}).store;

~pattern = Pseq([1,[3,[3,[1,2,1,1,1,1]],2]].convertRhythm);
~a = Pseq([0,2,4],inf);
~b = Prand([1,3,5,7]);

TempoClock.default.tempo = 1/2;

Pbind(
\instrument, \dynamik,
\scale, Scale.minor,
\dur, Pn(Plazy(~pattern)),
\start, Pstep([1,1/64],1/8,inf),
\legato, Pstep([1/6,1/2,0.95],1/3,inf),
\degree, Pstep([0,0,Prand([0,2,4]),Prand([1,3,5,7,9])],1/8,inf),
\wobble, TempoClock.default.tempo*Pwhite(1,8),
\octave, Pseq([2,3,4],inf) + 1
).play;

s.scope;
