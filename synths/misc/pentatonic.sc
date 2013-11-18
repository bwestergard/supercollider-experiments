{
var scale, rootnote, index, note, out, octave, modulations, div, trig, indices;
modulations = Dseq([5, 1, 4, 1, 3, 0, 0, 2], inf);
indices = Dseq([0, 3, 5, 3, 4, 5, 6, 7], inf);
scale = [0, 2, 5, 7, 9];
trig = Impulse.ar(6);
div = PulseDivider.ar(trig, 8, 0);
rootnote = (Demand.ar(div, 0, modulations) * 5) + 20;
index = Demand.ar(trig, 0, indices);
index = index + 13;
octave = (index / scale.size).floor(1);
note = (Select.kr(index % scale.size, scale) + rootnote + (octave * 12)) % 24;
note = note + 45;
out = LFTri.ar(note.midicps);
out = Clip.ar(out, -0.8, 0.9) * 0.3;
[out,out]
}.play;

{
var scale, rootnote, index, note, out, octave, modulations, div, trig, indices;
modulations = Dseq([5, 1, 4, 1, 3, 0, 0, 2], inf);
indices = Dseq([0, 3, 5, 3, 4, 5, 6, 7], inf);
scale = [0, 2, 5, 7, 9];
trig = Impulse.ar(6);
div = PulseDivider.ar(trig, 8, 0);
rootnote = (Demand.ar(div, 0, modulations) * 5) + 20;
index = Demand.ar(trig, 0, indices);
index = index + 10;
octave = (index / scale.size).floor(1);
note = (Select.kr(index % scale.size, scale) + rootnote + (octave * 12)) % 24;
note = note + 45;
out = LFTri.ar(note.midicps);
out = Clip.ar(out, -0.8, 0.9) * 0.3;
[out,out]
}.play;
