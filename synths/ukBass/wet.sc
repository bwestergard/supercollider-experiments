 
(
// modulating formant frequency
{ 
var in,wobble;
wobble = VarSaw.ar(1/2, width: 0.3);
in = VarSaw.ar(100, width: 0.5);
in = Formlet.ar(in, wobble.range(10,200), 0.05, 0.004);
in = Compander.ar(in, in, 0.3, 1, 1/80,1/100,1/100) * 8;
in = MoogLadder.ar(in, wobble.range(30,8000), 0.7);
in.dup
}.play;
)
