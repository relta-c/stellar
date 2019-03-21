#version 330 core

in vec2 fragTexCoords;

out vec4 fragColor;

uniform sampler2D spriteTex;
uniform vec3 spriteColor;

void main()
{
    fragColor = vec4(spriteColor, 1.0f) * texture(spriteTex, fragTexCoords);
}
