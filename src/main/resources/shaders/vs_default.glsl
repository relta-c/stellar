#version 330 core

layout (location = 0) in vec2 position;
layout (location = 1) in vec2 texCoords;// TODO : Remove this

out vec2 fragTexCoords;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
    fragTexCoords = position;
    gl_Position = projection * view * model * vec4(position, 0.0, 1.0);
}
