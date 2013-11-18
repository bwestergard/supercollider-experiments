s.boot;

(

x = SynthDef(\vibes, {arg note, gate, tremolo;

// Need to do something about lower registers. Filter? Less modulation? Both? Sharper decay for modulation?

var out, carrier, freq;

note = note + LFNoise2.ar(0.4, 1/12);

freq = note.midicps + SinOsc.ar(note.midicps * 6, mul: 100 * (1 - EnvGen.kr(Env.asr(0.4, 1, 0.4), gate, 1))) + SinOsc.ar(note.midicps * 5, mul: 1000 * (1 - EnvGen.kr(Env.asr(5, 1, 20), gate, 1)));

carrier = SinOsc.ar(freq) * 0.1 * ((SinOsc.ar(tremolo) / 2) + 1);

out = carrier * EnvGen.kr(Env.asr(0.01, 1, 0.1), gate, 1, doneAction: 2) * AmpComp.kr(note.midicps, 20.midicps) * 0.5;

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

newNode = Synth(\vibes);
newNode.set(\note, event.b);
newNode.set(\gate, 1);
newNode.set(\tremolo, 4);
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
