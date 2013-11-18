{
var s;
var freq = 45 * 0.5 * MouseButton.kr(3,8*0.midiratio);
var swarm = 0;
var n = 10;
n.do({
swarm = swarm + VarSaw.ar(freq * rrand(0.99,1.01), width: LFNoise2.ar(3).exprange(0.5,0.3));
});
swarm = swarm / n;
s = Splay.ar(swarm, 1) * AmpComp.kr(freq);
s = RLPF.ar(s, MouseX.kr(20,20000), 0.4) ;
}.play;
