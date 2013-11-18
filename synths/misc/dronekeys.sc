s.boot;

(

x = SynthDef(\filtpulse, {arg note, gate;

var out,tone,num;

num = 30;
tone = Silence.ar;
num.do({
tone = tone + Pan2.ar(VarSaw.ar((note.midicps + LFNoise2.ar(1/8, 1/12)) * Rand(1,4).round(1), 0, LFNoise2.ar(1/2).range(0.15, 0.5)), LFNoise2.ar(8).range(-1,1));
});
tone = tone * (1/num);
tone = Compander.ar(tone, tone, 1/10, 0.05, 0.05, 0.02, 0.02) * SinOsc.ar(LFNoise2.ar(1/30).range(0.1,1)).range(SinOsc.ar(LFNoise2.ar(1/30).range(0.1,4)).range(0.7,1),1);
out = tone * EnvGen.kr(Env.asr(0.1, 1, 0.3), gate, 1, doneAction: 2) * AmpComp.kr(note.midicps, 20.midicps);

Out.ar(0, out);

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
