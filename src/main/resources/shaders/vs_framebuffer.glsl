#version 330 core

layout (location = 0) in vec2 position;

out vec2 fragTexCoords;

void main()
{
    fragTexCoords = vec2((1 + position.s) / 2, (1 + position.t) / 2);// FIXME : Ugly hack
    gl_Position = vec4(position, 0.0, 1.0);
}

