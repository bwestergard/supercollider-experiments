
(

var a, b;

a = Pbind(
\instrument, \cfcf,
\scale, Scale.minor,
\octave, 7,
\amp, Pstep(Pseq([4/4, 3/4, 2/4, 1/4], inf), 1/4),

\degree, Pseq([Pstep(Pseq([0, 3, -5, -3], 1), 4)], inf)+40, 

\dur, PdurStutter(

Pseq([2,2,4],inf),

Prand([1/4,1/6,1/2],inf)

)

);

a.play;

)

(
SynthDef("cfcf", {
arg note = 40,dur,amp;
var saw,blok,tone,mod;
dur = dur * 4;
note = note + LFNoise2.ar(12).range(1/12,-1/12);
mod = SinOsc.ar(note.midicps*2) * XLine.ar(0.3,1/1000,dur);
tone = SinOsc.ar(note.midicps, phase: mod, mul: 0.5);
tone = tone * XLine.ar(1,1/10000,dur, doneAction:2) * 0.8 * amp;
Out.ar(0, tone.dup);
}).store;
)
