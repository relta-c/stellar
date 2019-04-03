#version 330 core

layout (location = 0) in vec4 vertex;// TODO : Optimize this

out vec2 fragTexCoords;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
    fragTexCoords = vertex.zw;
    gl_Position = projection * view * model * vec4(vertex.xy, 0.0, 1.0);
}
