
{
var note = Slew.ar(K2A.ar(MouseX.kr(40,75).round(5)),30,3000);
var mod = SinOsc.ar(note.midicps * 3) * XLine.ar(4,1/10,(1/8)*32);
mod = mod.tanh;
LeakDC.ar(TwoPole.ar(SinOsc.ar(note.midicps, phase: mod), SinOsc.ar(8).range(note.midicps/2,note.midicps*16) * 0.8, 0.93).dup) * 0.01}.play

