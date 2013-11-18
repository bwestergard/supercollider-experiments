
(

var a, chunks;

chunks = Pwrand([1, 2, 1.5, 1/3], [50, 50, 10, 10].normalizeSum, inf);

a = Pbind(
\instrument, \cfcf,
\scale, Scale.major,
\octave, 7,
\amp, Pstep(Pseq([Pgeom(1,0.90,16)],inf), 1/32),

\degree, Pseq([Pstep(Pseq([0, 3, 2, -4], 1), 4)], inf)+30, 

\dur, PdurStutter(

Pseq([Pconst(2, chunks),4],inf),

Pwrand([1/4,1/2,1.5,1,1/3], [10, 15, 1, 1, 1/2].normalizeSum, inf)

)

);

a.play;

)

(
SynthDef("cfcf", {
arg note = 40,dur,amp;
var saw,blok,tone,mod;
dur = dur*2;
note = note + LFNoise2.ar(12).range(1/12,-1/12);
mod = SinOsc.ar(note.midicps) * XLine.ar(2,1/1000,dur);
//tone = MoogLadder.ar(PinkNoise.ar,XLine.ar(Rand(4000,20000),20,dur),0.2) * 3;
tone = VarSaw.ar(note.midicps/2, width: XLine.ar(0.5,0.2,dur)) + VarSaw.ar(note.midicps, width: XLine.ar(0.5,0.3,dur));
tone = tone * XLine.ar(1,1/10000,dur, doneAction:2) * amp * XLine.ar(1/10000,1,0.003);
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
Out.ar(0, tone);
}).store;
)
