SynthDef(\mdslktr, {|note = 40, dur = 1, s = 20000, f = 20000, amp = 1|
var tone,out,mod;
dur = dur * 3;
mod = Pulse.ar(note.midicps, mul: XLine.ar(1,1/2,dur), width: XLine.ar(0.5,1/1000,dur));
tone = SinOsc.ar([note.midicps,note.midicps*1.005], phase:mod).dup * XLine.ar(1/10000,1,0.03) * XLine.ar(1,1/10000,dur,doneAction:2) * (1/16);
Out.ar(0, tone);
}).store()

var b = Pbind(
       \instrument, \mdslktr,
       \note, Array.series(7,0,2).degreeToKey([0,2,4,7,9])+50+Pstep([0,0,0,0,-2,-2,-2,-7],1,inf),
       \strum, Pstep([0,Prand([1/64,1/16],1)],1/2,inf).trace,
       \amp, 1,
       \s, Prand([20,100,200,400,20000],inf),
       \f, Prand([20,100,200,400,20000],inf),
       \dur, Pseq([1/2,1/2,1,1,1/4,1/4,1/4,1/4],inf)/2
       );
       
       Ppar([b]).play;

