 
(
// used to lag pitch
{
var tone = LFTri.ar(Lag3.ar(Pulse.ar(1).range(50,53).midicps, 0.01));
tone = MoogLadder.ar(tone, Lag3.ar(LFNoise0.ar(6).range(20,2000),0.01), 0.9);
tone.dup;
}.play
)
