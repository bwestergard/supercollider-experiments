SynthDef(\dynamik, {|freq = 440, sustain = 1, gate = 1, start = 1, end = 1|
var tone,env,wenv;
freq = freq * [1,1.01];
env = Env.adsr(0.03, 1/8, 0.9, 0.01, 1, -4);
tone = LFCub.ar(freq);
//tone = tone * LFPulse.ar(freq, width: 0.1).range(1,XLine.ar(1,2,sustain));
tone = (tone * 1.3).tanh;
tone = tone * EnvGen.kr(env, gate, doneAction: 2) * XLine.ar(start,end,sustain) * (2/3);
tone = tone * SinOsc.ar((4/3)/TempoClock.default.tempo).range(0,1);
Out.ar(0,tone);
}).store;

~pattern = Pseq([1,[3,[3,[1,2,1]],2]].convertRhythm);

TempoClock.default.tempo = 1/2;

Pbind(
\instrument, \dynamik,
\scale, Scale.minor,
\dur, Pn(Plazy(~pattern)),
\start, Pstep([1,1/64],1/8,inf),
\legato, Pstep([1/6,1/2,0.95],1/3,inf),
\degree, Pseq([0,2,4,3],inf),
\octave, 3
).play;

s.scope;
