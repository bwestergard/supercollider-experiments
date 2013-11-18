{
var tone,note;
note = 50;
tone = Mix ({ VarSaw.ar(note.midicps + LFNoise2.ar(0.1,1/12), width: Line.ar(0,3) + LFNoise2.ar(0.5, 0.1)) * 1/40 }!40) * 0.4;
tone = LinExp.ar(VarSaw.ar(3, width: SinOsc.ar(6/(128*4), mul: 1/2, add: 2))) * tone;

tone.dup;
}.play;

{
var tone,note;
note = 57;
tone = Mix ({ VarSaw.ar(note.midicps + LFNoise2.ar(0.1,1/12), width: Line.ar(0,3) + LFNoise2.ar(0.5, 0.1)) * 1/40 }!40) * 0.4;
tone = LinExp.ar(VarSaw.ar(2, width: SinOsc.ar(6/(128*4), mul: 1/2, add: 2))) * tone;

tone.dup;
}.play;
