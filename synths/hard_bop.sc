(
var a,b,swing,off,offset,the_scale,clicks;

TempoClock.default.tempo = 0.8;

off = 0.4;
swing = Pstep([1-off,1+off],1/8,inf);
the_scale = Scale.minor;

offset = Pstep(Pseries(0,-3,inf),4,inf);

clicks = Pbind(
       \instrument, \click,
       \amp, Pbjorklund(9,16,inf)*0.4,
       \dur, swing/16
       );

a = Pbind(
       \scale, the_scale,
       \instrument, \bouche,
       \div, 16,
       \dur, (2*swing)/Pkey(\div),
       \bjorklund, Pbjorklund(Prand([7,9,13,3],inf),Pkey(\div)),
       \amp, 0.4 * Pkey(\bjorklund),
       \degree, Pclutch(Pwalk(
Array.series(10, 0, 2), // integers, 0-19
// steps up to 2 in either direction, weighted toward positive
Pwrand([-4, -1, 0, 1, 2], [0.05, 0.1, 0.15, 1, 0.1].normalizeSum, inf),
// reverse direction at boundaries
Pseq([1, -1], inf),
10), Pkey(\bjorklund))
       );
       
b = Pbind(
       \scale, the_scale,
       \instrument, \bouche,
       \div, 8,
       \dur, swing/Pkey(\div),
       \bjorklund, Pbjorklund(Prand([5,3],inf),Pkey(\div)),
       \amp, (Pkey(\bjorklund) * 0.2 + Pseq([0.1,0.08],inf)) * 4,
       \degree, Pswitch1([Pseq([-7,-5,-16,-14,-13,-16,-16,-16],inf),Pseq([0,1,7],inf)-7 + [0,3,6,9], Pseq([2,3],inf)],Prand([0,0,2,0,0,0,0,1],inf),inf)+Pstep([0,2],8,inf),
       \octave, 6
       );
       
Ppar([a,b,clicks]).play;

)

SynthDef(\bouche, {| out = 0, bufnum = 0, rate = 1, amp = 1, dur = 1,sep = 0, freq = 440|
var note,tone,filtmod,resmod;
dur = dur * 3;
note = freq.cpsmidi;
tone = VarSaw.ar(note.midicps, width: XLine.ar(0.001,0.3,dur));
tone = (tone + Saw.ar((note+(LFNoise2.ar(2).range(-1,1)*0.3)).midicps))/2;
filtmod = XLine.ar(8000,20,dur,doneAction: 2) * XLine.ar(1/10000,1,dur/16);
resmod = XLine.ar(0.4,0.1,dur,doneAction: 2) * XLine.ar(1/10000,1,dur/32);
Out.ar(out, MoogLadder.ar(tone,filtmod,0.3).dup * amp)
}).store();

SynthDef(\click, {| out = 0, amp = 0, dur = 1 |
dur = dur * 5;
Out.ar(out, XLine.ar(1,1/1000,dur*2, doneAction:2) * BPF.ar(PinkNoise.ar,XLine.ar(20000,LinLin.kr(amp,0,1,200,1000),dur),XLine.ar(0.99,0.01,dur)).dup * 0.8 * amp);
}).store();
