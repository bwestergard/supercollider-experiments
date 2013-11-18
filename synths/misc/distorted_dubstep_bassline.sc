
{

var control,sig,note,phase_mod;
note = 40.cpsmidi;
control = MouseX.kr(0,1);
phase_mod = SinOsc.ar(note.midicps * 4) * 2;
sig = SinOsc.ar(note.midicps,0, 0.2, phase: phase_mod) + VarSaw.ar(note.midicps * 3 + LFNoise2.ar(4, 3),0, 0.3, phase: phase_mod);
sig = (sig * 8).softclip;
PrintVal.kr(control);

sig = MoogVCF.ar(sig, VarSaw.ar(6, 0, control, mul: 600, add: 700), 

0.8).dup;

}.play(s);
