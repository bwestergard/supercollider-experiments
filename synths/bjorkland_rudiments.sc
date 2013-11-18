(
var snare, kick, snare_part, clicks, swing, kick_part, swing_off, high, bass;

TempoClock.default.tempo = 1/2;

snare = Buffer.read(s, "/home/bjorn/audio_work/samples/budos_snare.wav");
kick = Buffer.read(s, "/home/bjorn/audio_work/samples/electro_kick.wav");


snare_part = Pbind(
       \instrument, \sinepluck,
       \div, 16,
       \dur, 1/Pkey(\div),
       \bjorklund, Pbjorklund(Pseries(5,1,inf)%Pkey(\div),Pkey(\div)).trace,
       \amp, Pkey(\bjorklund) * 0.15 * Pstep(Pseq([1,0.6],inf),1/4,inf),
       \degree, 4
       );
       
kick_part = Pbind(
       \instrument, \sinepluck,
       \div, 16,
       \dur, 1/Pkey(\div),
       \bjorklund, Pbjorklund(Pseries(2,1,inf)%Pkey(\div),Pkey(\div)).trace,
       \amp, Pkey(\bjorklund) * 0.15 * Pstep(Pseq([1,0.6],inf),1/4,inf),
       \degree, 0
       );

clicks = Pbind(
       \instrument, \click,
       \amp, 1,
       \dur, 1/4
       );
       
high = Pbind(
       \instrument, \sinepluck,
       \div, Pstep(Prand([8,16],inf),2,inf),
       \scale, Scale.major,
       \amp, (Pbjorklund(Prand([4,5,7],inf),Pkey(\div))) * 0.4 + 0.05,
       \dur, 1/(Pkey(\div)),
       \degree, Pstep([0,5],8,inf) + Pshuf([0,2,4,-1],inf),
       \out, 2,
       \octave, Pstep(Pseq([6,5],inf),1/2,inf)
);

bass = Pbind(\instrument, \ital_bass, \dur, 2, \octave, 3, \degree, Pseq([0,0,5,5],inf), \wobble, Prand([2/3,2,4,1/3],inf)*TempoClock.default.tempo );
       
Ppar([snare_part,kick_part]).play;

)
