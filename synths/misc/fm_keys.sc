s.boot;

(

x = SynthDef(\filtpulse, {arg note, gate;

var out, phase;

note = note + LFNoise2.ar(0.4, 1/12);

phase = SinOsc.ar(note.midicps * (5)) * (pi/6) * XLine.kr(1,0.3,3) + LFNoise2.ar(0.3,0.3);
phase = phase + SinOsc.ar(note.midicps * (7)) * (pi/2) * XLine.kr(1,0.1,3);

out = SinOsc.ar(note.midicps, phase: phase);
out = out * EnvGen.kr(Env.asr(0.01, 1, 1), gate, 1, doneAction: 2) * AmpComp.kr(note.midicps, 20.midicps) * 0.05;

Out.ar(0, out.dup);

}).send(s);

)


(

var notes, on, off;
MIDIIn.connect;
notes = Array.newClear(128);  // array has one slot per possible MIDI note

on = Routine({

var event, newNode;

loop {

event = MIDIIn.waitNoteOn;

newNode = Synth(\filtpulse);
newNode.set(\note, event.b);
newNode.set(\gate, 1);
notes.put(event.b, newNode);

}

}).play;


off = Routine({

var event;

loop {

event = MIDIIn.waitNoteOff;

notes[event.b].set(\gate, 0);

}

}).play;

q = { on.stop; off.stop; };

)

q.value;
