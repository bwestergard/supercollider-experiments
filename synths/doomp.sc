SynthDef(\doomp,{|sustain = 1, freq|
var tone,mod;
freq = freq.cpsmidi + LFNoise2.ar(20, mul: 0.2);
freq = freq.midicps * [1,1.001];
mod = SinOsc.ar(freq*2, mul: 0.3) * XLine.ar(2,1/1000,sustain/3);
mod = mod + SinOsc.ar(freq, mul: 0.1);
tone = SinOsc.ar(freq, phase: mod);
tone = tone * SinOsc.ar(freq*5).range(XLine.ar(1,1/200,sustain/2),1);
tone = tone * SinOsc.ar(freq*2).range(XLine.ar(1,1/2,sustain),1);
tone = tone * XLine.ar(1,1/100000,sustain,doneAction:2);
Out.ar(0, tone * 0.6);
}).store;

Pbind(
\instrument, \doomp,
\scale, Scale.major,
\dur, Pseq([1,[2,2,4,2,3,1]].convertRhythm,inf),
\degree, Pstep([-7,-14,Pwhite(4,7,1),2,4,4,5,3],2,inf),
\octave, 4 + [0,0,-1],
\legato, 4
).play;

SynthDef(\mousecrush,{
var input = In.ar(0, 2);
input = (input * MouseX.kr(1,10).poll).tanh;
ReplaceOut.ar(0, input);
}).store;

~filt = Synth(\mousecrush);
~filt.free;
