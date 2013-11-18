SynthDef(\mdslktr, {|note = 40, dur = 1 amp = 1, warble = 0.12|
var tone,out,mod;
dur = dur * 3;
mod = Pulse.ar(note.midicps, mul: XLine.ar(1,1/2,dur), width: XLine.ar(0.5,1/1000,dur));
tone = SinOsc.ar([(note+LFNoise2.ar(3).range(warble,-1*warble)).midicps,(note+LFNoise2.ar(3).range(warble,-1*warble)).midicps], phase:mod).dup * XLine.ar(1/10000,1,0.03) * XLine.ar(1,1/10000,dur,doneAction:2) * (1/16);
Out.ar(0, tone);
}).store()

var b = Pbind(
       \instrument, \mdslktr,
       \note, Array.series(8,0,2).degreeToKey([0,2,4,7,9])+50+Pstep([0,0,0,0,-2,-2,-2,-7],1,inf),
       \strum, Pstep([0,Pwhite(1/64,1/32,1)],1/2,inf),
       \amp, 1,
       \dur, Pseq([Pconst(5,1/2),1,1,Pconst(1,Pwrand([1/4,1/8,1/6],[3,10,1].normalizeSum,inf))],inf)
       ).play;
