{
var note = 50;
DynKlank.ar(`[[1, 2, 2/3, 3/2, 4, 7/2, 2/3]*note.midicps, nil, [0.3, 1/2, 1/2, 1/4, 0.3, 0.7, 0.1]*MouseX.kr(0.1,8)], 20*LPF.ar(Impulse.ar(4, 0, 0.4), 20, 0.1) * 2).dup * 3;
}.play;

{
var note = 53+12;
DynKlank.ar(`[[1, 2, 2/3, 3/2, 4, 7/2, 2/3]*note.midicps, nil, [0.3, 1/2, 1/2, 1/4, 0.3, 0.7, 0.1]*MouseX.kr(0.1,8)], 20*LPF.ar(Impulse.ar(8, pi, 0.4), 20, 0.1) * 2).dup * 3;
}.play;

{
var note = 57+12;
DynKlank.ar(`[[1, 2, 2/3, 3/2, 4, 7/2, 2/3]*note.midicps, nil, [0.3, 1/2, 1/2, 1/4, 0.3, 0.7, 0.1]*MouseX.kr(0.1,8)], 20*LPF.ar(Impulse.ar(8, pi/2, 0.4), 20, 0.1) * 2).dup * 3;
}.play;

Help.gui;
