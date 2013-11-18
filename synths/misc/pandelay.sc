
{
var tone,gate,n,time;
n = 12;
time = 3;
gate = Impulse.kr(1);
tone = AudioIn.ar(1)
Out.ar(0, Splay.arFill(n, { |i| DelayC.ar(tone,i/n * time,i/n * time)}));
}.play;
