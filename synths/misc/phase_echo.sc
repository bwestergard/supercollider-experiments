{
var pulse, out, tone, gate, freq, notes, note, octaves, feedback, clip, modulate;
freq = 2;
gate = Impulse.kr(freq);
notes = Drand([0, 5, 10, 15], inf);
octaves = Drand([1, 2, 3, 4], inf);
modulate = PulseCount.kr(PulseDivider.kr(gate, 12 * 4, 0)) * 7;
note = (Demand.kr(gate, 0, notes) - modulate) % 12;
PrintVal.kr(note);
note = note + 30 + (Demand.kr(gate, 0, octaves) * 12);
tone = VarSaw.ar(note.midicps, 0, LFNoise2.ar(12).range(0,1));
out = tone * Linen.kr(gate, 0.01, 1, LFNoise2.ar(1/20).range(0.1,0.2), doneAction: 0);
out = out + LocalIn.ar;
clip = LFNoise2.ar(1/10).range(0.1, 4);
//out = BLowPass.ar(out, LFNoise2.ar(3).range(300,2500), 2) ;
//out = (clip * out).tanh / clip;
LocalOut.ar(DelayC.ar(out,2*freq,(3/16)*(freq)) * 0.95);
out.dup * 0.3;
}.play;
