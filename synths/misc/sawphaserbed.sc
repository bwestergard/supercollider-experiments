{
var root = 57 - 24;
var glide_root;
var input_l,dsig_l, mixed_l;
var input_r,dsig_r, mixed_r;
var phase_control_l;
var phase_control_r;

glide_root = XLine.kr(40,root,40);

input_l = (Saw.ar(glide_root.midicps,0.1) + Saw.ar((glide_root + 7).midicps,0.05) + Saw.ar((glide_root - 7).midicps,0.05)) * ((LFTri.ar(7) * Line.kr(0.5, 0, 10)) + Line.kr(0.5, 0.8, 10));
phase_control_l = SinOsc.ar(LFNoise0.kr(4, 0.2, 0.3),0,0.005,0.008);
dsig_l = AllpassL.ar(input_l, 4, phase_control_l, 0);
mixed_l = input_l + dsig_l;

input_r = (Saw.ar(glide_root.midicps,0.1) + Saw.ar((glide_root + 7).midicps,0.05) + Saw.ar((glide_root - 7).midicps,0.05)) * ((LFTri.ar(7) * Line.kr(0.5, 0, 10)) + Line.kr(0.5, 0.8, 10));
phase_control_r = SinOsc.ar(LFNoise0.kr(4, 0.2, 0.3),0,0.005,0.08);
dsig_r = AllpassL.ar(input_r, 4, phase_control_r, 0);
mixed_r = input_r + dsig_r;
[mixed_l,mixed_r]
}.play

