// Simple

{
var base, trig, note, div, roots, foo;
base = 50;
trig = Impulse.ar(4);
note = 0;
note = note + (PulseCount.ar(trig) * 5);
note = (note % (12)) + base;
note = Lag.ar(note, 0.05);
foo = SinOsc.ar(note.midicps) * (1/8);
[foo,foo]
}.play();

// Only part of the cycle

{
var base, trig, note, div, roots, tonics, foo;
roots = Dseq([0, 5, 10, 5, 5], inf);
tonics = Dseq([0, 7, 3, 5], inf);
base = 50;
trig = TDuty.ar(Dseq([2, 1], inf) / 8);
div = PulseDivider.ar(trig, 12, 0);
note = 0;
note = note + Demand.ar(div, 0, roots);
note = note + Demand.ar(trig, 0, tonics);
note = (note % (24)) + base;
note = Lag.ar(note, 0.05);
foo = SinOsc.ar(note.midicps) * (1/8);
[foo,foo]
}.play();

{
var base, trig, note, div, roots, tonics, foo;
roots = Dseq([0, 5, 10, 5], inf);
tonics = Dseq([0, 7, 3, 5, 7, 7], inf);
base = 50;
trig = TDuty.ar(Dseq([2, 1, 1, 3], inf) / 10);
div = PulseDivider.ar(trig, 6, 0);
note = 0;
note = note + Demand.ar(div, 0, roots);
note = note + Demand.ar(trig, 0, tonics);
note = (note % (24)) + base;
note = Lag.ar(note, 0.05);
foo = SinOsc.ar(note.midicps) * (1/8);
foo = Clip.ar(foo, -1/20, 1/20);
[foo,foo]
}.play();

// Simpler Tests, Modulation alone, without chord changes

{
var base, trig, note, foo;
base = 50;
trig = TDuty.ar(Dseq([1, 1], inf) / 4);
note = base + ((PulseCount.ar(trig) * 5) % 12);
//note = Lag.ar(note, 0.1);
foo = SinOsc.ar(note.midicps) * 0.2;
[foo,foo]
}.play();

{
var base, trig, note, foo;
base = 45 + 7;
trig = TDuty.ar(Dseq([1, 1], inf) / 4);
note = base + ((PulseCount.ar(trig) * 5) % 12);
note = Lag.ar(note, 0.1);
foo = SinOsc.ar(note.midicps) * 0.2;
[foo,foo]
}.play();

{
var base, trig, note, foo;
base = 45 + 4;
trig = TDuty.ar(Dseq([1, 1], inf) / 4);
note = base + ((PulseCount.ar(trig) * 5) % 12);
//note = Lag.ar(note, 0.1);
foo = SinOsc.ar(note.midicps) * 0.2;
[foo,foo]
}.play();

{
var base, trig, note, foo;
base = 45 + 11;
trig = TDuty.ar(Dseq([1, 1], inf) / 4);
note = base + ((PulseCount.ar(trig) * 5) % 12);
//note = Lag.ar(note, 0.1);
foo = SinOsc.ar(note.midicps) * 0.2;
[foo,foo]
}.play();

