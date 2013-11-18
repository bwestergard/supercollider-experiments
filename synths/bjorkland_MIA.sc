(
var a,b,swing,off,offset,the_scale;

TempoClock.default.tempo = 0.75;

off = 0.05;
swing = Pstep([1-off,1+off],1/8,inf);
the_scale = Scale.mixolydian;

offset = Pstep(Pseries(0,-3,inf),4,inf);

a = Pbind(
       \scale, the_scale,
       \instrument, \sinepluck,
       \div, 8,
       \dur, swing/Pkey(\div),
       \bjorklund, Pbjorklund(Prand([9,13],inf),Pkey(\div)),
       \amp, Pbjorklund(3,Pkey(\div)) * 0.3 + 0.1,
       \degree, Prand([0,1,3],inf)
       );
       
b = Pbind(
       \scale, the_scale,
       \instrument, \sinepluck,
       \div, 8,
       \dur, swing/Pkey(\div),
       \bjorklund, Pbjorklund(5,Pkey(\div)),
       \amp, (Pkey(\bjorklund) * 0.4 + 0.1) * 1.5,
       \degree, Pseq([-7,-5,-16,-14,-13,-16,-16,-16],inf)
       );
       
Ppar([a,b]).play;

)
