s.boot;

z = Buffer.read(s, "/home/bjorn/audio_work/samples/j5.wav");


(

x = SynthDef(\specMap, {arg sndBuf, freeze = 0;

var a, b, chain1, chain2, out, carrier, freq;

a = LocalBuf.new(2048);

b = LocalBuf.new(2048);

freq = MouseX.kr(30,80).midicps + (SinOsc.ar(8) * 5);

carrier = LFSaw.ar(freq + LFNoise2.ar(16,5)) + LFSaw.ar((freq.cpsmidi + 4).midicps) + LFSaw.ar((freq.cpsmidi + 9).midicps) + (Pulse.ar(freq * 1/2) * 0.4) + (Pulse.ar(freq * 2/6) * 0.3);
carrier = carrier * 0.3;

chain1 = FFT(a, carrier); // to be filtered

chain2 = FFT(b, AudioIn.ar(1));

// mouse x to play with floor. 

chain1 = PV_SpectralMap(chain1, chain2, 0.0, MouseButton.kr(lag: 0), 1, 1);

out = IFFT(chain1) * 8;

Out.ar(0, out.dup);

}).play(s, [\sndBuf, z, \freeze, 0])

)


x.set(\freeze, 1);

x.set(\freeze, 0);


x.free;


z.free;
