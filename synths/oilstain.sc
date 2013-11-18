SynthDef(\swoop, {|freq = 400, dur = 1, sf = 20000, ef = 20000, sa = 1, ea = 0.2|
var tone,freq_env;
freq = (freq.cpsmidi + (LFNoise2.kr(12).range(-1,1) * (1/8))).midicps;
freq = freq * [1,1.001];
tone = LFPar.ar(freq * [1,1.0002], width: 0.05) * 0.3;
tone = tone * XLine.ar(0.0001,1,0.005);
tone = tone * XLine.kr(sa,ea,dur,doneAction:2);
freq_env = XLine.ar(sf,ef,dur);
tone = RLPF.ar(tone,freq_env, XLine.ar(0.8,0.2,dur)) /2;
Out.ar(0, tone);
}).store;

Synth(\swoop, [\freq, 40.midicps]);

Help.gui

TempoClock.default.tempo = 1;

a = Pbind(
\degree, Pseq([4,0,3,0],inf) + [0,2,4], 
\sa, Pseq([0.2,1/10000],inf),
\ea, Pseq([1/10000,1],inf),
\dur, Pseq([1,1], inf) * 2,
\sf, Pwhite(20,2000),
\ef, Pwhite(20,2000),
\instrument, \swoop,
\octave, 2 + [-1,0,2,3]
);

b = Pbind(
\degree, 7, 
\sa, Pseq([0.2,1/10000],inf),
\ea, Pseq([1/10000,1/10000],inf),
\dur, Pseq([1,1], inf),
\sf, Pwhite(20,2000),
\ef, Pwhite(20,2000),
\instrument, \swoop,
\octave, 3 + [-1,0,2,3]
);

Ppar([ a, b ]).play;
