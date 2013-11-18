SynthDef(\bouche, {| out = 0, bufnum = 0, rate = 1, amp = 1, dur = 1,sep = 0, freq = 440|
var note,tone,filtmod,resmod;
dur = dur * 6;
note = freq.cpsmidi;
tone = VarSaw.ar(note.midicps, width: XLine.ar(0.001,0.3,dur));
tone = (tone + Saw.ar((note+(LFNoise2.ar(2).range(-1,1)*0.3)).midicps))/2;
filtmod = XLine.ar(20000,20,dur,doneAction: 2) * XLine.ar(1/10000,1,dur/16);
resmod = XLine.ar(0.4,0.1,dur,doneAction: 2) * XLine.ar(1/10000,1,dur/32);
Out.ar(out, MoogLadder.ar(tone,filtmod,0.3).dup * amp * SinOsc.ar(2*(dur/Rand(2,4).round(1))).range(0,2))
}).store();

Ptpar([16,Pbind(
       \instrument, \bouche,
       \div, 16,
       \bjorklund, Pbjorklund(Pseq([7,5,13],inf),Pkey(\div)),
       \dur, 2/Pkey(\div),
       \amp, Pkey(\bjorklund),
       \degree, Pstep([0,-2,0,[-2,-4+7]],1,inf)+[0,7]+Prand([0,0,0,0,0,5],inf),
       \octave, 3
      ),
      0,Pbind(
       \instrument, \bouche,
       \div, 8,
       \bjorklund, Pbjorklund(Pseq([7,5,13],inf),Pkey(\div)),
       \dur, 1,`
       \amp, Pkey(\bjorklund),
       \degree, Pstep([0,-2,0,[-2,-4+7]],1,inf),
       \octave, 6
      )]
       ).play;
