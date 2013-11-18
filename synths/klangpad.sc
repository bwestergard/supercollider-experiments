SynthDef(\klangpad, {|note = 40, velocity = 64, filter = 64|
var tone,dur;
dur = LinLin.kr(velocity,1,128,1/2,1);
note = LFNoise2.ar(12).range(1,-1) * (1/12) + note;
tone = VarSaw.ar(note.midicps) * XLine.ar(1,1/1000,dur,doneAction:2);
tone = tone * LinLin.kr(velocity,1,128,1/1000,1) * 0.3;
tone = MoogLadder.ar(tone, XLine.ar(1/1000,1,0.01)* XLine.ar(20000,20,dur), 0.3);
tone = HPF.ar(tone, 200, 0.1) * 8;
Out.ar(0,tone);
}).store();

Synth(\klangpad, [\note, 50, \velocity, 128]);

MIDIIn.connect;

MIDIIn.noteOn = {|port,chan,note,vel|
(note < 62).if({Synth(\klangpad, [\note, note;, \velocity, vel]);},{"wait!".postln});
note.postln;
};

MIDIIn.noteOn = nil;

c = CCResponder({ |src,chan,num,value|
[src,chan,num,value].postln;
(num == 74).if({~filt = value;"Filter!".postln;});
});

Help.gui;
