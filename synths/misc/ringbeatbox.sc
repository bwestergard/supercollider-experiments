{
var in,out,freq,hasFreq;
in = AudioIn.ar(1);
# freq, hasFreq = Tartini.kr(in);
out = XFade2.ar(in * SinOsc.ar(freq*(1/2)),in,LFTri.ar(2));
[out,out]
}.play
