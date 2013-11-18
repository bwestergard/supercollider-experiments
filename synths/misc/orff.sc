{
var freq = (40).midicps;
var mod = Pulse.ar(freq*(4/2), width: XLine.ar(1,1/10000,2)) * XLine.ar(1/10000,1,0.01) * XLine.ar(1,1/10000,5);
var tone;
mod = mod + (SinOsc.ar(freq*1) * XLine.ar(1/10000,2,1) * XLine.ar(2,1/10000,2));
tone = SinOsc.ar(freq, phase: mod * 3.14);
tone = BLowPass.ar(tone, XLine.ar(20000,freq*2,2), XLine.ar(0.1,2,2));
Out.ar(0, tone.dup * 0.1 * XLine.ar(1,1/10000,1));
}.play;

{
var freq = (47).midicps;
var mod = Pulse.ar(freq*(4/2), width: XLine.ar(1,1/10000,2)) * XLine.ar(1/10000,1,0.01) * XLine.ar(1,1/10000,5);
var tone;
mod = mod + (SinOsc.ar(freq*1) * XLine.ar(1/10000,2,1) * XLine.ar(2,1/10000,2));
tone = SinOsc.ar(freq, phase: mod * 3.14);
tone = BLowPass.ar(tone, freq*8, 0.1);
Out.ar(0, tone.dup * 0.1 * XLine.ar(1,1/10000,1));
}.play;


{
var freq = (49).midicps;
var mod = Pulse.ar(freq*(4/2), width: XLine.ar(1,1/10000,2)) * XLine.ar(1/10000,1,0.01) * XLine.ar(1,1/10000,5);
var tone;
mod = mod + (SinOsc.ar(freq*1) * XLine.ar(1/10000,2,1) * XLine.ar(2,1/10000,2));
tone = SinOsc.ar(freq, phase: mod * 3.14);
tone = BLowPass.ar(tone, freq*8, 0.1);
Out.ar(0, tone.dup * 0.1 * XLine.ar(1,1/10000,1));
}.play;

{
var freq = (44+12).midicps;
var mod = Pulse.ar(freq*(4/2), width: XLine.ar(1,1/10000,2)) * XLine.ar(1/10000,1,0.01) * XLine.ar(1,1/10000,5);
var tone;
mod = mod + (SinOsc.ar(freq*1) * XLine.ar(1/10000,2,1) * XLine.ar(2,1/10000,2));
tone = SinOsc.ar(freq, phase: mod * 3.14);
tone = BLowPass.ar(tone, freq*8, 0.1);
Out.ar(0, tone.dup * 0.1 * XLine.ar(1,1/10000,1));
}.play;
