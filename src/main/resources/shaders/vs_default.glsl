#version 330 core

layout (location = 0) in vec2 position;
layout (location = 1) in vec2 texCoords;

out vec2 fragTexCoords;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
    fragTexCoords = texCoords;
    //fragTexCoords = vec2(1.0 - texCoords.x, 1.0 - texCoords.y);
    gl_Position = projection * view * model * vec4(position, 0.0, 1.0);
}
