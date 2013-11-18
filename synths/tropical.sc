Server.local.options.memSize = (8192*8);

(
// chords. If an Array of pitches is returned by a Stream for pitch, then a chord
// will be played.
var scale,bass,high,a,off,snare;
off = 0.1;
a = Pseq([1+off,1-off],inf);

TempoClock.default.tempo = 60/60;
scale = Scale.major;

bass = Pbind(
\instrument, \kick,
\amp, (Pbjorklund(Pseq([2,2,2,3],inf),8)) * 0.7,
\dur, a/8
);

high = Pbind(
       \instrument, \sinepluck,
       \div, Pwrand([Pn(16, 16), Pn(32, 32), Pn(24, 24)],[20,1,2].normalizeSum,inf).trace,
       \amp, (Pbjorklund(Prand([5,3,7],inf).trace,Pkey(\div))),
       \dur, Pif(Pkey(\amp) == 0, -1,1) * a/(Pkey(\div)),
       \note, Pstep([[Prand([0,-2]),3,5,10],[0,3,7,10]],2,inf),
       \octave, 5
);

Ppar([bass,high]).play;
)

SynthDef("kick", { | amp = 1, dur |
var tone;
tone = SinOsc.ar(XLine.ar(800,2,dur*4, mul: 0.2, doneAction:2));
Out.ar(0, amp * tone.dup * XLine.ar(2,1/1000,dur*4));
}).store;


(
SynthDef("sinepluck", {
arg freq = 440, amp = 1, dur;
var mod,tone;
amp = amp * 0.8;
dur = 1/8;
mod = VarSaw.ar(freq*2, mul: XLine.ar(0.2,0.8,dur/2), width: XLine.ar(1,0.95,dur/2));
tone = SinOsc.ar(freq, mod).dup * 0.1 * XLine.ar(1,1/1000,dur*2, doneAction:2) * amp;
tone = tone.dup * XLine.ar(1/10000,1,0.005);
Out.ar(0,tone);
}).store;
)
