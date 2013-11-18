TempoClock.default.tempo = 70/60;

var b = [8,[1,1,[2,[1,1,1,1]]]].convertRhythm;
var a = [8,[1!12].flatten].convertRhythm;

var chords = Pbind(
       \instrument, \cresh,
       \s, 1/1000,
       \f, 1,
       \dur, Pseq([b,a].flatten,inf),
       \degree, Pstep([[0],[1,7],2,[7,9]],1,inf),
       \octave, 4
);

var drone = Pbind(
       \instrument, \cresh,
       \f, 1/64,
       \s, 1,
       \dur, 2,
       \degree, Pwhite(1,20,inf),
       \octave, 2
);

Ppar([chords,drone]).play;

SynthDef(\cresh, {|freq = 440, s = 1, f = 1, dur, gate = 1|
var tone;
tone = VarSaw.ar(freq * [1,LFNoise2.ar(2).range(1,1.01)], width: LinLin.kr(freq,200,4000,0.5,0));
tone = tone * XLine.ar(s,f,dur) * 0.1 * SinOsc.ar(16/dur).range(0.8,1);
tone = tone * Linen.kr(gate, 0.001, 80, 0.001, doneAction:2);
Out.ar(0, tone);
}).store;

Help.gui
