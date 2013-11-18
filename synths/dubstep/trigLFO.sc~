(
x = { var trig, numSmp, rate, freq, wobble,out,note,mod;
       freq = (140/60);
       trig = Impulse.kr(freq);
       note = Demand.kr(trig, 0, Drand([40, 40, 40, 43, 43, 43, 47, 49, 53], inf)) + Demand.kr(trig, 0, Drand([12, 0, 0, 0, 0, 0], inf));
       note = Slew.kr(note, 200, 30);
       numSmp = SampleRate.ir / freq;
       rate = 2pi / numSmp;
       rate = rate * (Demand.kr(trig, 0, Drand([0.5, 1, 2, 3, 4, 6], inf)) * Demand.kr(trig, 0, Drand([1,1/2,1/3,1/4], inf)));
       wobble = Phasor.ar(trig, rate, pi/2, pi/2+2pi ).cos;
       out = VarSaw.ar(note.midicps, width: wobble.range(0.45,0.55)) + SinOsc.ar(note.midicps/2, mul: wobble.range(0,1)).dup;
       out = Decimator.ar(out, 20000, wobble.range(2,8));
       out = MoogLadder.ar(out, wobble.range(note.midicps,25000), wobble.range(0.03,0.2)).dup;
       out = out * 0.3;
}.play;
)
