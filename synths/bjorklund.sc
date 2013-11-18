Server.local.options.memSize = (8192*8);

(
// chords. If an Array of pitches is returned by a Stream for pitch, then a chord
// will be played.
var scale,bass,high,a,off,snare;
off = 0.1;
a = Pstep([1+off,1-off],inf);

TempoClock.default.tempo = 60/60;
scale = Scale.major;

bass = Pbind(
\instrument, \kick,
\amp, (Pbjorklund(Pseq([2,2,2,3],inf),8)) * 0.7,
\dur, 1/8
);

high = Pbind(
       \instrument, \sinepluck,
       \div, 16,
       \amp, (Pbjorklund(Pseq([9,9,9,5],inf),Pkey(\div))),
       \dur, Pif(Pkey(\amp) == 0, -1,1) * 2/(Pkey(\div)),
       \note, Pstep([[0,4,7],[3,7,10]],2,inf),
       \octave, 5
);

snare = Pbind(
       \instrument, \snare,
       \div, 16,
       \amp, Pbjorklund(5,8),
       \dur, 1/16
);

Ppar([bass,high]).play;
)

Help.gui;

Pseq([Pbjorklund(9,16,inf),Pbjorklund(5,12,inf)],inf) * 2

SynthDef("kick", { | amp = 1, dur |
var tone;
tone = SinOsc.ar(XLine.ar(800,2,dur*4, mul: 0.2, doneAction:2));
Out.ar(0, amp * tone.dup * XLine.ar(2,1/1000,dur*4));
}).store;

SynthDef("snare", { | amp = 1, dur |
dur = dur * 16;
Out.ar(0, amp * XLine.ar(2,1/1000,dur) * BPF.ar(PinkNoise.ar(0.8), XLine.ar(20000,1000,dur, doneAction:2), 0.8).dup);
}).store;


(
SynthDef("sinepluck", {
arg freq = 440, amp = 1, dur;
var mod,tone;
amp = amp * 0.8;
dur =1/6;
mod = VarSaw.ar(freq*2, mul: XLine.ar(0.2,0.8,dur/2), width: XLine.ar(1,1/1000,dur*64));
tone = SinOsc.ar(freq, mod).dup * 0.1 * XLine.ar(1,1/1000,dur*4, doneAction:2) * XLine.ar(1/1000,1,0.001) * amp;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * XLine.ar(1/10000,1,0.005);
Out.ar(0,tone);
}).store;
)
