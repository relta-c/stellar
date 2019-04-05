#version 330 core

in vec2 fragTexCoords;

out vec4 fragColor;

uniform sampler2D sprite;
uniform vec3 color;
uniform float transparency;

void main()
{
    fragColor = vec4(color.rgb * transparency, 1 * transparency);
}
