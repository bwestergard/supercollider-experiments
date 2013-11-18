(

var y;

b = Bus.control(s);
q = Bus.control(s);

MIDIIn.noteOn = { arg src, chan, num, vel;
[chan,num,vel].postln;
x = Synth(\snare, [\note, num, \amp, 1, \mod, 0]);
y = Synth(\snare, [\note, num+12, \amp, 1, \mod, 0]); y.map(2,b.index);
x.map(2,b.index);
y.map(2,b.index);
x.map(1,q.index);
y.map(1,q.index);
};

MIDIIn.control = { arg src, chan, num, val; [chan,num,val].postln;
"control".postln;
if(num == 7, {b.value = val;"7".postln;}, {q.value = val;});
}
)

(

x = SynthDef(\snare, {arg note, dur, mod;
var tone,amount;
note = note+12;

dur = LinLin.kr(dur,0,127,0.1,1);
amount = LinLin.kr(mod,0,127,0,2);
amount = XLine.kr(amount,1/1000,dur/16);
mod = (SinOsc.ar(note.midicps*2) * Slew.kr(amount,80,80)) + LocalIn.ar;
tone = SinOsc.ar(note.midicps/2, phase: mod) * XLine.ar(1, 1/1000, dur, doneAction: 2) * 0.3;
LocalOut.ar(tone*amount);

Out.ar(0, tone.dup);

}).store;

)

Synth(\snare, [\freq, 800, \amp, 1]);
