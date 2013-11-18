(
var a,b,swing,off,offset,the_scale;

TempoClock.default.tempo = 2/3;

off = 0.1;
swing = Pstep([1-off,1+off],1/4,inf);
the_scale = Scale.mixolydian;

offset = Pstep(Pseries(0,-3,inf),4,inf);

a = Pbind(
       \scale, the_scale,
       \instrument, \sinepluck,
       \div, 16,
       \dur, swing/Pkey(\div),
       \bjorklund, Pbjorklund(Prand([9,13],inf),Pkey(\div)),
       \amp, Pswitch1([Pkey(\bjorklund), Pseq([1,0.8],inf)], Pstep(Prand([0,1],inf), 1/2, inf)) * 0.15 * Pstep(Pseq([1,0.6],inf),1/4,inf) * 1.5,
       \degree, (offset + Pstep(Pshuf([0,4,1],inf),1/3,inf)) % 14 + [0,3],
       \octave, 5
       );
       
b = Pbind(
       \scale, the_scale,
       \instrument, \sinepluck,
       \div, 8,
       \dur, swing/Pkey(\div),
       \bjorklund, Pbjorklund(5,Pkey(\div)),
       \amp, (Pkey(\bjorklund) * 0.3 + 0.1) * 1.5,
       \degree, (Pseq([-7,-5,-16,-14,-13,-16,-16,-16],inf) + offset) % (7*2) - (7*2)
       );
       
Ppar([a,b]).play;

)
