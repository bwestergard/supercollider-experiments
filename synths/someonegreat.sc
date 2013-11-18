SynthDef(\swoop, {|freq = 400, dur = 1, sf = 20000, ef = 20000, sa = 1, ea = 0.2|
var tone,freq_env;
freq = freq * [1,1.001];
tone = VarSaw.ar(freq * [1,1.0002], width: 0.05) * 0.3;
tone = tone * XLine.ar(0.0001,1,0.005);
tone = tone * XLine.kr(sa,ea,dur,doneAction:2);
freq_env = XLine.ar(sf,ef,dur);
tone = RLPF.ar(tone,freq_env, XLine.ar(0.8,0.2,dur));
Out.ar(0, tone);
}).store;

Synth(\swoop, [\freq, 40.midicps]);

Help.gui

TempoClock.default.tempo = 1;

a = Pbind(
\degree, 5, 
\dur, Pseq([1/8,1/8,3/8,3/8,3/8,3/8,2/8], inf),
\instrument, \swoop,
\sf, Pn(Pwhite(20,2000,inf),8),
\ef, Pn(Pwhite(20,2000,inf),8),
\octave, 3
);

b = Pbind(
\degree, Pseq([0,0,0,3],inf) + [0,2,4], 
\sa, Pseq([0,1/10000],inf),
\ea, Pseq([1/10000,1],inf),
\dur, Pseq([0.25,0.75], inf) * 4,
\sf, 200,
\ef, 2000,
\instrument, \swoop,
\octave, 2 + [0,1,2,3]
);

Ppar([ a, b ]).play;
