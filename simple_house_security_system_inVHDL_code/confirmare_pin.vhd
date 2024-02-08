library ieee;
use ieee.std_logic_1164.all;

entity confirmare_pin is
	port(a,b,c,d:in integer;
	x0,x1,x2,x3:in integer;
	--enable,enable2,enable3:in std_logic;
	z:out std_logic);
end entity;

architecture cmp of confirmare_pin is
begin
	process(a,b,c,d,x0,x1,x2,x3)
	begin
		
			if(a=x0) then
				z<='1';
			else z<='0';
			end if;
			if(b=x1) then
				z<='1';
			else z<='0';
			end if;
			if(c=x2) then
				z<='1';
			else z<='0';
			end if;
			if(d=x3) then
				z<='1';
			else z<='0';
			end if;
			
			end process;
			end architecture;